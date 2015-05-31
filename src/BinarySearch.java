/*
 * Binary search solves the problem [of searching within a pre-sorted array]
 * by keeping track of a range within the array in which T [i.e. the sought value]
 * must be if it is anywhere in the array.  Initially, the range is the entire
 * array.  The range is shrunk by comparing its middle element to T and
 * discarding half the range.  The process continues until T is discovered
 * in the array, or until the range in which it must lie is known to be empty.
 * In an N-element table, the search uses roughly log(2) N comparisons.
 *
 */
public class BinarySearch
{
    public static int search( Integer aNeedle, Integer[] aHaystack )
    {
        if ( aHaystack.length == 0 )
        {
            return -1;
        }

        return interiorSearch( aNeedle, 0, aHaystack.length - 1, aHaystack );
    }

    private static Boolean indexOutsideHaystackBounds( int anIndex, Integer[] aHaystack )
    {
        return anIndex < 0 || anIndex > aHaystack.length - 1;
    }

    private static int interiorSearch( Integer aNeedle,
                                           int aStartIndex,
                                           int anEndIndex,
                                           Integer[] aHaystack )
    {
        if ( aStartIndex == anEndIndex )
        {
            if ( aHaystack[aStartIndex].equals( aNeedle ) )
            {
                return aStartIndex;
            }
        }
        else if ( aStartIndex > anEndIndex
                || indexOutsideHaystackBounds( aStartIndex, aHaystack )
                || indexOutsideHaystackBounds( anEndIndex, aHaystack ) )
        {
            return -1;
        }

        int theMiddleIndex = aStartIndex + ( ( anEndIndex - aStartIndex ) / 2 );
        int theCompareResult = aHaystack[theMiddleIndex].compareTo( aNeedle );
        if ( theCompareResult == 0 )
        {
            return theMiddleIndex;
        }
        else if ( theCompareResult < 0 )
        {
            // search top half of range
            return interiorSearch( aNeedle, theMiddleIndex + 1, anEndIndex, aHaystack );
        }
        else
        {
            // search bottom half of range
            return interiorSearch( aNeedle, aStartIndex, theMiddleIndex - 1, aHaystack );
        }
    }
}
