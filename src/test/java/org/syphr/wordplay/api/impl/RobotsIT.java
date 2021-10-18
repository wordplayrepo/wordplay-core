package org.syphr.wordplay.api.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.syphr.wordplay.api.Board;
import org.syphr.wordplay.api.Configuration;
import org.syphr.wordplay.api.Location;
import org.syphr.wordplay.api.Piece;
import org.syphr.wordplay.api.Robot;
import org.syphr.wordplay.api.TileSet;
import org.syphr.wordplay.api.TileSetFactory;
import org.syphr.wordplay.api.ValuedPlacement;

@Ignore
public class RobotsIT
{
    private static AbstractRobot robotThreaded;
    private static AbstractStreamRobot robotStreamed;
    private static Board board;

    @BeforeClass
    public static void setup()
    {
        Configuration config = new ConfigurationBuilder().build();

        robotThreaded = new AbstractRobot()
        {};
        configureRobot(robotThreaded, config);

        robotStreamed = new AbstractStreamRobot();
        configureRobot(robotStreamed, config);

        board = new BoardImpl(config.getBoardDimension(),
                              config.getOrientations(),
                              config.getBoardStart(),
                              tileSetFactory(config),
                              new WordFactoryImpl(),
                              config.getDictionary(),
                              new ScoreCalculatorImpl(config.getRackSize(), config.getRackBonus()));
    }

    private static TileSetFactory tileSetFactory(Configuration config)
    {
        return new TileSetFactory()
        {
            @Override
            public TileSet createTileSet()
            {
                return new AbstractTileSet()
                {
                    {
                        getTile(Location.at(4, 2)).setPiece(new TestPiece(EnglishLetter.H));
                        getTile(Location.at(4, 3)).setPiece(new TestPiece(EnglishLetter.A));
                        getTile(Location.at(4, 4)).setPiece(new TestPiece(EnglishLetter.M));
                        getTile(Location.at(4, 5)).setPiece(new TestPiece(EnglishLetter.M));
                        getTile(Location.at(4, 6)).setPiece(new TestPiece(EnglishLetter.E));
                        getTile(Location.at(4, 7)).setPiece(new TestPiece(EnglishLetter.R));
                    }

                    @Override
                    protected Configuration getConfiguration()
                    {
                        return config;
                    }
                };
            }
        };
    }

    private static void configureRobot(Robot robot, Configuration config)
    {
        robot.setConfiguration(config);
        robot.setStrategy(new HighestScoreStrategy());

        RackImpl rack = new RackImpl(robot.getConfiguration().getRackSize())
        {
            @Override
            public List<Piece> getPieces()
            {
                return Arrays.asList(new TestPiece(EnglishLetter.A),
                                     new TestPiece(EnglishLetter.C),
                                     new TestPiece(EnglishLetter.E),
                                     new TestPiece(EnglishLetter.T),
                                     new TestPiece(null, true));
            }
        };
        robot.setRack(rack);
    }

    @Test
    public void testRobots()
    {
        long start, end;

        start = System.currentTimeMillis();
        robotThreaded.findPlacements(board);
        end = System.currentTimeMillis();
        System.out.println("Threaded took " + (end - start) + " ms");

        start = System.currentTimeMillis();
        Collection<ValuedPlacement> placements = robotStreamed.getStrategy().getDataStructure();
        robotStreamed.findPlacements(board, placements);
        end = System.currentTimeMillis();
        System.out.println("Streamed took " + (end - start) + " ms");

        Assert.assertEquals(robotThreaded.getStrategy().getDataStructure().size(), placements.size());
    }
}
