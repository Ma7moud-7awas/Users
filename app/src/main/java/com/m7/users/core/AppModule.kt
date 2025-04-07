package com.m7.users.core

import android.content.Context
import com.m7.users.data.UsersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext appContext: Context
    ) = UsersDatabase.getDatabase(appContext.applicationContext)

    @Singleton
    @Provides
    fun provideUserDAO(db: UsersDatabase) = db.userDAO()
}