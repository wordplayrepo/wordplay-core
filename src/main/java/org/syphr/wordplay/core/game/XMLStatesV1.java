package org.syphr.wordplay.core.game;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.namespace.QName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.wordplay.core.component.Piece;
import org.syphr.wordplay.core.component.Tile;
import org.syphr.wordplay.core.config.Configurations;
import org.syphr.wordplay.core.player.Player;
import org.syphr.wordplay.core.xml.JaxbFactory;
import org.syphr.wordplay.core.xml.JaxbUtils;
import org.syphr.wordplay.core.xml.SchemaVersion;
import org.syphr.wordplay.core.xml.UnsupportedSchemaVersionException;

class XMLStatesV1
{
    private static final Logger LOGGER = LoggerFactory.getLogger(XMLStatesV1.class);

    private static final JaxbFactory<org.syphr.wordplay.xsd.v1.State> STATE_FACTORY_V1 = JaxbFactory.create(org.syphr.wordplay.xsd.v1.State.class);

    private static final JaxbFactory<org.syphr.wordplay.xsd.v1.PlayerType> PLAYER_FACTORY_V1 = JaxbFactory.create(org.syphr.wordplay.xsd.v1.PlayerType.class,
                                                                                                                  new QName(SchemaVersion._1.getStateNamespace(),
                                                                                                                            "player"));

    public static org.syphr.wordplay.xsd.v1.State read(File file) throws IOException
    {
        LOGGER.trace("Reading base state from \"{}\"", file.getAbsolutePath());
        return STATE_FACTORY_V1.read(file);
    }

    public static org.syphr.wordplay.xsd.v1.State read(InputStream in) throws IOException
    {
        LOGGER.trace("Reading base state from a stream");
        return STATE_FACTORY_V1.read(in);
    }

    public static org.syphr.wordplay.xsd.v1.State create(Game game)
    {
        LOGGER.trace("Creating state from a game");

        org.syphr.wordplay.xsd.v1.State state = new org.syphr.wordplay.xsd.v1.State();

        org.syphr.wordplay.xsd.v1.BoardLayoutType boardLayout = new org.syphr.wordplay.xsd.v1.BoardLayoutType();
        for (Tile tile : game.getBoard().getTiles().getOccupiedTiles()) {
            Piece piece = tile.getPiece();

            org.syphr.wordplay.xsd.v1.PieceType pieceType = new org.syphr.wordplay.xsd.v1.PieceType();
            pieceType.setLetter(piece.getLetter().toString());
            pieceType.setWild(piece.isWild());

            org.syphr.wordplay.xsd.v1.TilePlacementType tilePlacement = new org.syphr.wordplay.xsd.v1.TilePlacementType();
            tilePlacement.setLocation(JaxbUtils.toLocation(tile.getLocation()));
            tilePlacement.setPiece(pieceType);

            boardLayout.getTiles().add(tilePlacement);
        }
        state.setBoard(boardLayout);

        org.syphr.wordplay.xsd.v1.PlayersType playersType = new org.syphr.wordplay.xsd.v1.PlayersType();
        Player currentPlayer = game.getCurrentPlayer();
        if (currentPlayer != null) {
            playersType.setCurrent(currentPlayer.getId().toString());
        }
        try {
            for (Player player : game.getPlayers()) {
                String xml = player.serialize(SchemaVersion._1);
                org.syphr.wordplay.xsd.v1.PlayerType playerType = PLAYER_FACTORY_V1.read(xml);

                playersType.getPlayers().add(playerType);
            }
        } catch (UnsupportedSchemaVersionException e) {
            throw new IllegalArgumentException("The player objects in this game are incompatible with this schema version",
                                               e);
        } catch (IOException e) {
            throw new IllegalArgumentException("The player objects in this game cannot be saved", e);
        }
        state.setPlayers(playersType);

        // TODO get rid of V1 coupling
        state.setConfiguration(Configurations.toV1BaseConfig(game.getConfiguration()));

        // TODO extensions

        return state;
    }

    public static XMLStateV1 create(org.syphr.wordplay.xsd.v1.State baseState)
    {
        LOGGER.trace("Creating state from a base state");
        return new XMLStateV1(baseState);
    }

    public static void write(XMLStateV1 state, File file) throws IOException
    {
        LOGGER.trace("Writing state to \"{}\"", file.getAbsolutePath());
        write(state.getExternalState(), file);
    }

    public static void write(XMLStateV1 state, OutputStream out) throws IOException
    {
        LOGGER.trace("Writing state to a stream");
        write(state.getExternalState(), out);
    }

    public static void write(org.syphr.wordplay.xsd.v1.State baseState, File file) throws IOException
    {
        LOGGER.trace("Writing state base to \"{}\"", file.getAbsolutePath());
        STATE_FACTORY_V1.write(baseState, file);
    }

    public static void write(org.syphr.wordplay.xsd.v1.State baseState, OutputStream out) throws IOException
    {
        LOGGER.trace("Writing state base to a stream");
        STATE_FACTORY_V1.write(baseState, out);
    }

    private XMLStatesV1()
    {
        /*
         * Factory pattern
         */
    }
}
