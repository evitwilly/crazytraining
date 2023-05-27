package ru.freeit.crazytraining.core.theming

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.freeit.crazytraining.core.cache.PersistentIntStorage

internal class CoreThemeManagerTest {

    class TestPersistenceSimpleDataStorage(var value: Int) : PersistentIntStorage {
        override fun int(key: String, default: Int) = value
        override fun save(key: String, value: Int) {
            this.value = value
        }
    }

    @Test
    fun `test when theme has been saved in cache`() {
        val cache = TestPersistenceSimpleDataStorage(CoreTheme.DARK.ordinal)
        val manager = CoreThemeManager(cache)
        assertEquals(CoreTheme.DARK, manager.selected_theme)
    }

    @Test
    fun `test when theme has been changed`() {
        val cache = TestPersistenceSimpleDataStorage(CoreTheme.LIGHT.ordinal)
        val manager = CoreThemeManager(cache)

        manager.changeTheme(CoreTheme.DARK)

        assertEquals(CoreTheme.DARK, manager.selected_theme)
        assertEquals(CoreTheme.DARK.ordinal, cache.value)

        manager.changeTheme(CoreTheme.LIGHT)

        assertEquals(CoreTheme.LIGHT, manager.selected_theme)
        assertEquals(CoreTheme.LIGHT.ordinal, cache.value)
    }

    @Test
    fun `test when theme manager has been recreated`() {
        val cache = TestPersistenceSimpleDataStorage(CoreTheme.LIGHT.ordinal)
        val manager1 = CoreThemeManager(cache)

        assertEquals(CoreTheme.LIGHT, manager1.selected_theme)

        manager1.changeTheme(CoreTheme.DARK)

        val manager2 = CoreThemeManager(cache)

        assertEquals(CoreTheme.DARK, manager2.selected_theme)
    }

}