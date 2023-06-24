package ru.freeit.crazytraining.core.mocks

import ru.freeit.crazytraining.core.viewmodel.SavedInstanceState

class SavedInstanceStateMock : SavedInstanceState {

    override fun <T> parcelable(key: String, `class`: Class<T>): T? = null

}