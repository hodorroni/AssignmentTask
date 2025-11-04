package com.rony.assignment.core.domain

import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toKotlinInstant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Instant.formatToReadableString(
    pattern: String = "yyyy-MM-dd HH:mm"
): String {
    val formatter = DateTimeFormatter
        .ofPattern(pattern)
        .withZone(ZoneId.systemDefault())

    return formatter.format(this.toJavaInstant())
}

fun String.toKotlinInstant(
    pattern: String = "yyyy-MM-dd HH:mm"
): Instant {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    val localDateTime = LocalDateTime.parse(this, formatter)
    val javaInstant = localDateTime
        .atZone(ZoneId.systemDefault())
        .toInstant()
    return javaInstant.toKotlinInstant()
}