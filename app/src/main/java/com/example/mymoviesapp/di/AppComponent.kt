package com.example.mymoviesapp.di

import android.content.Context
import com.example.data.di.NetworkModule
import com.example.mymoviesapp.presentation.fragment.MovieDetailsFragment
import com.example.mymoviesapp.presentation.fragment.MoviesFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        NetworkModule::class,
        MoviesUseCaseModule::class,
        RepositoryModule::class,
        DatabaseModule::class,
        SharedPreferencesModule::class,
    ]
)
@Singleton
interface AppComponent {

    fun inject(moviesFragment: MoviesFragment)
    fun inject(movieDetailsFragment: MovieDetailsFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}