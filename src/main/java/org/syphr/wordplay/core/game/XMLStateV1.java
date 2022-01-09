package org.syphr.wordplay.core.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.syphr.wordplay.core.component.Bag;
import org.syphr.wordplay.core.component.Board;
import org.syphr.wordplay.core.component.NoSuchPieceException;
import org.syphr.wordplay.core.component.Piece;
import org.syphr.wordplay.core.component.Rack;
import org.syphr.wordplay.core.config.Configuration;
import org.syphr.wordplay.core.config.Configurations;
import org.syphr.wordplay.core.lang.Letter;
import org.syphr.wordplay.core.lang.LetterFactory;
import org.syphr.wordplay.core.player.Player;
import org.syphr.wordplay.core.player.Robot;
import org.syphr.wordplay.core.player.RobotStrategy;
import org.syphr.wordplay.core.space.Location;
import org.syphr.wordplay.core.xml.JaxbUtils;
import org.syphr.wordplay.xsd.v1.PieceType;
import org.syphr.wordplay.xsd.v1.PlayerType;
import org.syphr.wordplay.xsd.v1.PlayersType;
import org.syphr.wordplay.xsd.v1.TilePlacementType;

class XMLStateV1
{
    private final org.syphr.wordplay.xsd.v1.State externalState;

    private Map<Location, Piece> tiles;

    private PlayersInfo playersInfo;

    private Configuration configuration;

    private Map<String, Object> extensions;

    /* default */ XMLStateV1(org.syphr.wordplay.xsd.v1.State externalState)
    {
        this.externalState = externalState;
    }

    /* default */ org.syphr.wordplay.xsd.v1.State getExternalState()
    {
        return externalState;
    }

    public void resumeGame(Game game)
    {
        setupBoard(game.getBoard(), game.getBag());
        setupPlayers(game);
    }

    protected void setupBoard(Board board, Bag bag)
    {
        if (tiles == null) {
            tiles = getTiles(externalState.getBoard().getTiles(), bag, getConfiguration());
        }

        for (Entry<Location, Piece> entry : tiles.entrySet()) {
            board.getTiles().getTile(entry.getKey()).setPiece(entry.getValue());
        }
    }

    protected void setupPlayers(Game game)
    {
        Bag bag = game.getBag();
        LetterFactory letterFactory = getConfiguration().getLetterFactory();

        for (Player player : getPlayersInfo().getPlayers()) {
            game.addPlayer(player);

            Rack rack = player.getRack();
            rack.clear(bag);

            List<String> letters = getPlayersInfo().getRacks().get(player.getId());
            if (letters != null) {
                for (String letter : letters) {
                    try {
                        rack.add(bag.getPiece(letterFactory.toLetter(letter)));
                    } catch (NoSuchPieceException e) {
                        // TODO temp workaround for compatibility until proper handling is determined
                        throw new IllegalArgumentException(e);
                    }
                }
            }
        }

        /*
         * Once the current player is set, the game is considered to be started and no
         * more players can be added. Therefore, set the current player after adding all
         * players.
         */
        UUID currentPlayerId = getPlayersInfo().getCurrentPlayer();
        if (currentPlayerId != null) {
            for (Player player : getPlayersInfo().getPlayers()) {
                if (player.getId().equals(getPlayersInfo().getCurrentPlayer())) {
                    game.setCurrentPlayer(player);
                    break;
                }
            }
        }
    }

    protected PlayersInfo getPlayersInfo()
    {
        if (playersInfo == null) {
            playersInfo = new PlayersInfo();

            PlayersType players = externalState.getPlayers();

            String currentPlayerId = players.getCurrent();
            if (currentPlayerId != null) {
                playersInfo.setCurrentPlayer(UUID.fromString(currentPlayerId));
            }

            for (PlayerType playerType : players.getPlayers()) {
                Player player = JaxbUtils.getInstance(playerType.getType());
                player.setId(UUID.fromString(playerType.getId()));
                player.setName(playerType.getName());
                player.addScore(playerType.getScore());

                if (playerType.isResigned()) {
                    player.resign();
                }

                for (PieceType piece : playerType.getRack().getPieces()) {
                    playersInfo.addLetter(player.getId(), piece.getLetter());
                }

                // TODO remove reference to Robot & RobotStrategy from the core
                if (player instanceof Robot) {
                    Robot robot = (Robot) player;
                    robot.setConfiguration(getConfiguration());
                    robot.setStrategy(JaxbUtils.<RobotStrategy>getInstance(playerType.getStrategy()));
                }

                playersInfo.addPlayer(player);
            }
        }

        return playersInfo;
    }

    public Configuration getConfiguration()
    {
        if (configuration == null) {
            configuration = Configurations.create(externalState.getConfiguration());
        }

        return configuration;
    }

    public Map<String, Object> getExtensions()
    {
        if (extensions == null) {
            extensions = JaxbUtils.getExtensions(externalState.getExtensions());
        }

        return Collections.unmodifiableMap(extensions);
    }

    protected Map<Location, Piece> getTiles(List<TilePlacementType> externalTiles, Bag bag, Configuration configuration)
    {
        Map<Location, Piece> tiles = new HashMap<Location, Piece>();

        for (TilePlacementType externalTile : externalTiles) {
            tiles.put(JaxbUtils.getLocation(externalTile.getLocation()),
                      getPiece(externalTile.getPiece(), bag, configuration));
        }

        return tiles;
    }

    protected List<Piece> getPieces(List<PieceType> externalPieces, Bag bag, Configuration configuration)
    {
        List<Piece> pieces = new ArrayList<Piece>();

        for (PieceType externalPiece : externalPieces) {
            pieces.add(getPiece(externalPiece, bag, configuration));
        }

        return pieces;
    }

    protected Piece getPiece(PieceType piece, Bag bag, Configuration configuration)
    {
        Letter letter = configuration.getLetterFactory().toLetter(piece.getLetter());
        try {
            return bag.getPiece(letter);
        } catch (NoSuchPieceException e) {
            // TODO temp workaround for compatibility until proper handling is determined
            throw new IllegalArgumentException(e);
        }
    }

    private static class PlayersInfo
    {
        private final List<Player> players = new ArrayList<Player>();
        private UUID currentPlayer;

        private final Map<UUID, List<String>> racks = new HashMap<UUID, List<String>>();

        public void addPlayer(Player player)
        {
            players.add(player);
        }

        public List<Player> getPlayers()
        {
            return players;
        }

        public void setCurrentPlayer(UUID currentPlayer)
        {
            this.currentPlayer = currentPlayer;
        }

        public UUID getCurrentPlayer()
        {
            return currentPlayer;
        }

        public void addLetter(UUID playerId, String letter)
        {
            List<String> rack = racks.get(playerId);
            if (rack == null) {
                rack = new ArrayList<String>();
                racks.put(playerId, rack);
            }

            rack.add(letter);
        }

        public Map<UUID, List<String>> getRacks()
        {
            return racks;
        }
    }
}
