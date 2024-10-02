package com.example.getirlite.model

import com.example.getirlite.view.fragments.cart.CartDatabase
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDateTime

enum class User {
    userId, userName, email, phone,
    didSeeOnboarding, didLogIn,
    favouriteProducts, cartItems;

    val string: String get() = string(key = name)
    val stringSet: Set<String> get() = stringSet(key = name)
    val mutableSet: MutableSet<String> get() = mutableSet(key = name)
    val list: ArrayList<String> get() = list(key = name)
    val bool: Boolean get() = bool(key = name)
    val int: Int get() = int(key = name)
    val float: Float get() = float(key = name)
    val double: Double get() = double(key = name)
    val long: Long get() = long(key = name)
    val jsonObject: JSONObject get() = jsonObject(key = name)
    val jsonArray: JSONArray get() = jsonArray(key = name)

    fun set(value: String) = set(key = name, value = value)
    fun set(value: ArrayList<String>) = set(key = name, value = value)
    fun set(value: Set<String>) = set(key = name, value = value)
    fun set(value: Boolean) = set(key = name, value = value)
    fun set(value: Int) = set(key = name, value = value)
    fun set(value: Float) = set(key = name, value = value)
    fun set(value: Double) = set(key = name, value = value)
    fun set(value: Long) = set(key = name, value = value)

    fun bool(defValue: Boolean = false): Boolean = bool(key = name, defValue = defValue)

    fun add(element: String) {
        val set = mutableSet
        set.add(element)
        set(set)
    }

    fun addIfNotPresent(element: String) {
        val set = mutableSet
        if (set.contains(element)) return
        set.add(element)
        set(set)
    }

    fun remove(element: String) {
        val set = mutableSet
        set.remove(element)
        set(set)
    }

    fun addOrRemove(element: String) {
        val set = mutableSet
        set.addOrRemove(element)
        set(set)
    }

    fun contains(element: String): Boolean = contains(key = name, element = element)

    val exists: Boolean get() = exists(key = name)

    fun remove() = User.remove(name)
    fun increment() = set(int + 1)

    companion object {
        fun string(key: String): String = UserData.data.getString(key, "") ?: ""
        fun stringSet(key: String): Set<String> = HashSet(UserData.data.getStringSet(key, HashSet()) ?: HashSet())
        fun mutableSet(key: String): MutableSet<String> = stringSet(key = key) as MutableSet
        fun list(key: String): ArrayList<String> = ArrayList(stringSet(key = key))
        fun bool(key: String, defValue: Boolean = false): Boolean = UserData.data.getBoolean(key, defValue)
        fun int(key: String): Int = UserData.data.getInt(key, 0)
        fun float(key: String): Float = UserData.data.getFloat(key, 0f)
        fun double(key: String): Double = float(key = key).toDouble()
        fun long(key: String): Long = UserData.data.getLong(key, 0)

        fun jsonObject(key: String): JSONObject = try { JSONObject(string(key = key)) } catch (_: Exception) { JSONObject() }
        fun jsonArray(key: String): JSONArray = try { JSONArray(string(key = key)) } catch (_: Exception) { JSONArray() }

        fun set(key: String, value: String) = UserData.editor.putString(key, value).apply()
        fun set(key: String, value: ArrayList<String>) = UserData.editor.putStringSet(key, HashSet(value)).apply()
        fun set(key: String, value: Set<String>) = UserData.editor.putStringSet(key, value).apply()
        fun set(key: String, value: Boolean) = UserData.editor.putBoolean(key, value).apply()
        fun set(key: String, value: Int) = UserData.editor.putInt(key, value).apply()
        fun set(key: String, value: Float) = UserData.editor.putFloat(key, value).apply()
        fun set(key: String, value: Double) = UserData.editor.putFloat(key, value.toFloat()).apply()
        fun set(key: String, value: Long) = UserData.editor.putLong(key, value).apply()

        fun contains(key: String, element: String): Boolean = stringSet(key = key).contains(element)
        fun exists(key: String): Boolean = UserData.data.contains(key)

        fun remove(key: String) = UserData.editor.remove(key).apply()

        fun bomb() {
            userName.remove()
            email.remove()
            phone.remove()
            cartItems.remove()
            favouriteProducts.remove()
        }
    }
}
fun MutableSet<String>.addOrRemove(element: String) = if (contains(element)) remove(element) else add(element)