package bdkosher.goodtimes

import java.time.Month

/**
 * Static extension methods for java.time.Month
 */
class MonthStaticExtension {

    /**
     * The values of the Month enum but starting with the given month.
     */
    static Month[] values(final Month type, Month start) {    
        Month.values().collect { it + start.value - 1} as Month[]
    }    

    /**
     * Calls the closure for each month of the year, starting with January.
     */
    static void eachMonth(final Month type, Closure closure) {
        eachMonth(type, Month.JANUARY, closure)
    }

    /**
     * Calls the closure for each month of the year, starting with the given month.
     */
    static void eachMonth(final Month type, Month start, Closure closure) {
        def paramTypes = closure.parameterTypes
        boolean acceptsMonth = paramTypes.length > 0 && paramTypes[0].isAssignableFrom(Month)
        Month.values(start).each { month ->
            acceptsMonth ? closure(month) : closure()
        }
    }
}