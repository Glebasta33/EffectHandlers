package com.gltrusov.effects_lib.utils

import android.content.Context
import com.gltrusov.effects_lib.R
import java.lang.reflect.Method

//TODO: Удалить после создания utils-библиотеки
/**
 * Создать ссылку на функцию на уровне файла (не класса) в GitHub.
 *
 * @param context = LocalContext.current
 * @param pack = object {}::class.java.`package`
 * @param method = object {}::class.java.enclosingMethod
 */
fun createLinkToFileFunction(context: Context, pack: Package?, method: Method?): String {
    val packageName = pack?.name?.replace('.', '/') ?: throw error("package == null")
    val funName = method?.name?.replace('.', '/') ?: throw error("method == null")
    val baseUrl = createBasicUrl(context)
    return "$baseUrl/$packageName/$funName.kt"
}

/**
 * Создать ссылку на класс в GitHub.
 *
 * @param className = this.javaClass.name
 * @param context контекст
 */
fun createLinkToClass(className: String, context: Context): String {
    val baseUrl = createBasicUrl(context)
    val currentFileName = className.replace('.', '/')
    return "$baseUrl/$currentFileName.kt"
}

/**
 * Создать html-ссылку на класс в GitHub
 *
 * @param className = this.javaClass.name
 * @param context контекст
 */
fun createHtmlLinkClass(className: String, context: Context): String {
    val link = createLinkToClass(className, context)
    return "<a href='$link'>Link</a>"
}


private fun createBasicUrl(context: Context): String {
    val appName = context.getString(R.string.app_name)
    return "https://github.com/Glebasta33/$appName/blob/master/app/src/main/java"
}