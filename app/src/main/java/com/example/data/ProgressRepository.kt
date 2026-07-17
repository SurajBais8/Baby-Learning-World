package com.example.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ProgressRepository(private val progressDao: ProgressDao) {

    val progressFlow: Flow<UserProgress> = progressDao.getProgressFlow().map { 
        it ?: UserProgress()
    }

    suspend fun getProgress(): UserProgress {
        return progressDao.getProgress() ?: UserProgress()
    }

    suspend fun saveProgress(progress: UserProgress) {
        progressDao.saveProgress(progress)
    }

    suspend fun addStars(stars: Int) {
        val current = getProgress()
        val updated = current.copy(totalStars = current.totalStars + stars)
        saveProgress(updated)
    }

    suspend fun updateStreak() {
        val current = getProgress()
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val todayStr = sdf.format(Date())

        if (current.lastActiveDate == todayStr) {
            // Already updated today
            return
        }

        val lastDateStr = current.lastActiveDate
        val isYesterday = if (lastDateStr.isEmpty()) {
            false
        } else {
            try {
                val lastDate = sdf.parse(lastDateStr) ?: Date(0)
                val diff = Date().time - lastDate.time
                val diffDays = diff / (24 * 60 * 60 * 1000)
                diffDays in 1..2
            } catch (e: Exception) {
                false
            }
        }

        val newStreak = if (isYesterday) current.currentStreak + 1 else 1
        val updated = current.copy(
            currentStreak = newStreak,
            lastActiveDate = todayStr
        )
        saveProgress(updated)
    }

    suspend fun toggleLanguage() {
        val current = getProgress()
        val newLang = if (current.lang == "en") "hi" else "en"
        saveProgress(current.copy(lang = newLang))
    }

    suspend fun changeLanguage(langCode: String) {
        val current = getProgress()
        saveProgress(current.copy(lang = langCode))
    }

    suspend fun toggleDarkMode() {
        val current = getProgress()
        saveProgress(current.copy(isDarkMode = !current.isDarkMode))
    }

    suspend fun unlockSticker(stickerId: String) {
        val current = getProgress()
        val stickers = current.unlockedStickers.split(",").toMutableSet()
        if (stickers.add(stickerId)) {
            saveProgress(current.copy(unlockedStickers = stickers.joinToString(",")))
        }
    }

    suspend fun markModuleCompleted(moduleId: String) {
        val current = getProgress()
        val modules = current.completedModules.split(",").toMutableSet()
        if (modules.add(moduleId)) {
            saveProgress(current.copy(completedModules = modules.joinToString(",")))
        }
    }
}
