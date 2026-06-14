package vn.anhbt.nutripath.core.util

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

fun LocalDate.toInstantRange(
    zoneId: ZoneId = ZoneId.systemDefault()
): Pair<Instant, Instant> {
    val startInstant = this
        .atStartOfDay(zoneId)
        .toInstant()

    val endInstant = this
        .plusDays(1)
        .atStartOfDay(zoneId)
        .toInstant()

    return startInstant to endInstant
}
