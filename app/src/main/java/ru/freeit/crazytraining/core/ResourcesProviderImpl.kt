package ru.freeit.crazytraining.core

import android.content.res.Resources

class ResourcesProviderImpl(private val resources: Resources) : ResourcesProvider {

    override fun string(stringResource: Int, vararg args: Any): String {
        return resources.getString(stringResource, *args)
    }

    override fun quantityString(stringResource: Int, quantity: Int, vararg args: Any): String {
        return resources.getQuantityString(stringResource, quantity, *args)
    }

}