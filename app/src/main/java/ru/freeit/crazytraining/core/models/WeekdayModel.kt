package ru.freeit.crazytraining.core.models

import androidx.annotation.StringRes
import ru.freeit.crazytraining.R

enum class WeekdayModel(@StringRes val stringResource: Int, val calendarVariable: Int) {
    MONDAY(R.string.monday, 0),
    TUESDAY(R.string.tuesday, 1),
    WEDNESDAY(R.string.wednesday, 2),
    THURSDAY(R.string.thursday, 3),
    FRIDAY(R.string.friday, 4),
    SATURDAY(R.string.saturday, 5),
    SUNDAY(R.string.sunday, 6);
}