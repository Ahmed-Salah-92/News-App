package com.ragdoll.newsapp.presentation

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// This class is annotated with @HiltAndroidApp to enable Hilt dependency injection
@HiltAndroidApp
class NewsApp : Application()