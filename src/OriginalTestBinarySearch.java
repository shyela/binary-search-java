import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;


public class OriginalTestBinarySearch
{
    private Random myRandom;

    @Before
    public void setUp()
    {
        myRandom = new Random();
    }

    @Test
    public void emptyArray()
    {
        Integer[] theHaystack = new Integer[] {};
        assertEquals( -1, BinarySearch.search( 1, theHaystack ) );
    }

    @Test
    public void singleElement()
    {
        Integer[] theHaystack = new Integer[] { 1 };
        assertEquals( -1, BinarySearch.search( 0, theHaystack ) );
        assertEquals( 0, BinarySearch.search( 1, theHaystack ) );
        assertEquals( -1, BinarySearch.search( 2, theHaystack ) );
    }

    @Test
    public void duplicateElements()
    {
        Integer[] theHaystack = new Integer[] { 0, 0, 0, 0, 1, 1, 1, 2, 2 };
        assertEquals( -1, BinarySearch.search( -1, theHaystack ) );
        assertResultIsOneOf( new Integer[]{ 0, 1, 2, 3 }, BinarySearch.search( 0, theHaystack ) );
        assertEquals( 4, BinarySearch.search( 1, theHaystack ) );
        assertEquals( 7, BinarySearch.search( 2, theHaystack ) );
        assertEquals( -1, BinarySearch.search( 3, theHaystack ) );
    }

    @Test
    public void duplicateElementsThatMatchNeedle()
    {
        Integer[] theHaystack = new Integer[] { 1, 1, 1 };
        assertEquals( -1, BinarySearch.search( 0, theHaystack ) );
        assertResultIsOneOf( new Integer[]{ 0, 1, 2 }, BinarySearch.search( 1, theHaystack ) );
        assertEquals( -1, BinarySearch.search( 2, theHaystack ) );
    }

    @Test
    public void duplicateElementsThatDontMatchNeedle()
    {
        Integer[] theHaystack = new Integer[] { 3, 3, 3 };
        assertEquals( -1, BinarySearch.search( 0, theHaystack ) );
        assertEquals( -1, BinarySearch.search( 1, theHaystack ) );
        assertEquals( -1, BinarySearch.search( 2, theHaystack ) );
    }

    // Broke initially
    @Test
    public void twoAndThreeElements()
    {
        Integer[] theHaystack = new Integer[] { 1, 2 };
        assertEquals( -1, BinarySearch.search( 0, theHaystack ) );
        assertEquals( 0, BinarySearch.search( 1, theHaystack ) );
        assertEquals( 1, BinarySearch.search( 2, theHaystack ) );
        assertEquals( -1, BinarySearch.search( 3, theHaystack ) );

        theHaystack = new Integer[] { 0, 1, 2 };
        assertEquals( 0, BinarySearch.search( 0, theHaystack ) );
        assertEquals( 1, BinarySearch.search( 1, theHaystack ) );
        assertEquals( 2, BinarySearch.search( 2, theHaystack ) );
    }

    @Test
    public void aBunchOfElements()
    {
        Integer[] theHaystack = new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        assertEquals( -1, BinarySearch.search( -1, theHaystack ) );
        assertEquals( 0, BinarySearch.search( 0, theHaystack ) );
        assertEquals( 1, BinarySearch.search( 1, theHaystack ) );
        assertEquals( 4, BinarySearch.search( 4, theHaystack ) );
        assertEquals( 8, BinarySearch.search( 8, theHaystack ) );
        assertEquals( 9, BinarySearch.search( 9, theHaystack ) );
        assertEquals( -1, BinarySearch.search( 10, theHaystack ) );
        assertEquals( -1, BinarySearch.search( 40, theHaystack ) );
        assertEquals( -1, BinarySearch.search( 90, theHaystack ) );
    }

    // Added because of breaking random test
    @Test
    public void missingNeedleThatIsAnInteriorValue()
    {
        Integer[] theHaystack = new Integer[] { 0, 0, 1, 3, 4, 5, 6, 7, 8, 9, 10 };
        assertEquals( -1, BinarySearch.search( -1, theHaystack ) );
        assertEquals( 0, BinarySearch.search( 0, theHaystack ) );
        assertEquals( 2, BinarySearch.search( 1, theHaystack ) );
        assertEquals( -1, BinarySearch.search( 2, theHaystack ) );
        assertEquals( 3, BinarySearch.search( 3, theHaystack ) );
        assertEquals( 4, BinarySearch.search( 4, theHaystack ) );
        assertEquals( 5, BinarySearch.search( 5, theHaystack ) );
        assertEquals( 8, BinarySearch.search( 8, theHaystack ) );
        assertEquals( 9, BinarySearch.search( 9, theHaystack ) );
        assertEquals( 10, BinarySearch.search( 10, theHaystack ) );
        assertEquals( -1, BinarySearch.search( 40, theHaystack ) );
        assertEquals( -1, BinarySearch.search( 90, theHaystack ) );
    }

    private void assertResultIsOneOf( Integer[] anArray, Integer aResult )
    {
        assertTrue( Arrays.binarySearch( anArray, aResult ) >= 0 );
    }

    @Test
    public void runAgainstRandomlyGeneratedArrays() throws Exception
    {
        long theCumulativeSize = 0;
        long theCumulativeTimeForMe = 0;
        long theCumulativeTimeForSun = 0;

        for ( int i = 0; i < 40; i++ )
        {
            int theHaystackSize = myRandom.nextInt( 20 );
            theCumulativeSize += theHaystackSize;
            List<Integer> theWorkingHaystack = new ArrayList<Integer>();

            boolean theFillHaystackWithSequentialIntegers = myRandom.nextBoolean();
            if ( theFillHaystackWithSequentialIntegers )
            {
                fillListWithSequentialIntegersLessThanMax( theWorkingHaystack, theHaystackSize );
            }
            else
            {
                fillListWithRandomIntegersLessThanMax( theWorkingHaystack, theHaystackSize );
                Collections.sort( theWorkingHaystack );
            }

            Integer theNeedle = null;

            boolean theHaystackWillContainTheNeedle = ( theHaystackSize > 0 ) && myRandom.nextBoolean();
            if ( theHaystackWillContainTheNeedle )
            {
                theNeedle = theWorkingHaystack.get( myRandom.nextInt( theHaystackSize ) );
            }
            else
            {
                List<Integer> theMissingNeedles = new ArrayList<Integer>();
                fillListWithSequentialIntegersLessThanMax( theMissingNeedles, theHaystackSize );
                theMissingNeedles.removeAll( theWorkingHaystack );
                theMissingNeedles.add( -1 );
                theMissingNeedles.add( theHaystackSize );
                theMissingNeedles.add( theHaystackSize + 1 );
                theNeedle = theMissingNeedles.get( myRandom.nextInt( theMissingNeedles.size() ) );
            }

            Integer[] theHaystack = theWorkingHaystack.toArray( new Integer[0] );

            int theResult = BinarySearch.search( theNeedle, theHaystack );
            if ( theHaystackWillContainTheNeedle )
            {
                assertEquals( theNeedle, theHaystack[ theResult ] );
                int theSunResult = Arrays.binarySearch( theHaystack, theNeedle );
                assertTrue( 0 <= theSunResult && theSunResult < theHaystackSize );
            }
            else
            {
                assertEquals( -1, theResult );
                int theSunResult = Arrays.binarySearch( theHaystack, theNeedle );
                assertTrue( theSunResult < 0 || theSunResult > theHaystackSize );
            }

//            long theStartNanos = System.nanoTime();
//            BinarySearch.search( theNeedle, theHaystack );
//            theCumulativeTimeForMe += System.nanoTime() - theStartNanos;
//
//            theStartNanos = System.nanoTime();
//            Arrays.binarySearch( theHaystack, theNeedle );
//            theCumulativeTimeForSun += System.nanoTime() - theStartNanos;
        }

//        System.out.println( theCumulativeSize );
//        System.out.println( theCumulativeTimeForMe );
//        System.out.println( theCumulativeTimeForSun );
    }

    private void fillListWithRandomIntegersLessThanMax( List<Integer> aList, int aMaxInteger )
    {
        for ( int j = 0; j < aMaxInteger; j++ )
        {
            aList.add( j, myRandom.nextInt( aMaxInteger ) );
        }
    }

    private void fillListWithSequentialIntegersLessThanMax( List<Integer> aList, int aMaxInteger )
    {
        for ( int j = 0; j < aMaxInteger; j++ )
        {
            aList.add( j, j );
        }
    }
}
