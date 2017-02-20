package bdkosher.goodtimes

import java.time.*

/**
 * Static extension methods for java.time.DayOfWeek
 */
class DayOfWeekStaticExtension {

    /**
     * The values of the DayOfWeek enum but starting with the given day.
     */
    static DayOfWeek[] values(final DayOfWeek type, DayOfWeek start) {    
        DayOfWeek.values().collect { it + start.value - 1} as DayOfWeek[]
    }    

    /**
     * Calls the closure for each day of the week, starting with Sunday.
     */
    static eachDay(final DayOfWeek type, Closure closure) {
        eachDay(type, DayOfWeek.SUNDAY, closure)
    }

    /**
     * Calls the closure for each day of the week, starting with the given day.
     */
    static eachDay(final DayOfWeek type, DayOfWeek start, Closure closure) {
        def paramTypes = closure.parameterTypes
        boolean acceptsDayOfWeek = paramTypes.length > 0 && paramTypes[0].isAssignableFrom(DayOfWeek)
        DayOfWeek.values(start).each { day ->
            acceptsDayOfWeek ? closure(day) : closure()
        }
    }
}