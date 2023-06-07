package ru.freeit.crazytraining.core.models

import androidx.annotation.StringRes
import ru.freeit.crazytraining.R

enum class WeekdayModel(@StringRes val stringResource: Int, val calendarVariable: Int) {
    MONDAY(R.string.monday, 2),
    TUESDAY(R.string.tuesday, 3),
    WEDNESDAY(R.string.wednesday, 4),
    THURSDAY(R.string.thursday, 5),
    FRIDAY(R.string.friday, 6),
    SATURDAY(R.string.saturday, 7),
    SUNDAY(R.string.sunday, 1);
}