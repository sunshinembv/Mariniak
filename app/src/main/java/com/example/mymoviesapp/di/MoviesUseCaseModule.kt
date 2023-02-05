package com.example.mymoviesapp.di

import com.example.domain.repository.AppInfoRepository
import com.example.domain.repository.MovieDatabaseRepository
import com.example.domain.repository.MovieRepository
import com.example.domain.usecases.GetLoadPagesUseCase
import com.example.domain.usecases.GetMovieByIdUseCase
import com.example.domain.usecases.GetMoviesFromDbUseCase
import com.example.domain.usecases.GetPopularMoviesUseCase
import com.example.domain.usecases.GetTimeUpdateUseCase
import com.example.domain.usecases.InsertMoviesUseCase
import com.example.domain.usecases.RemoveFavoriteMovieByIdUseCase
import com.example.domain.usecases.RemovePopularMoviesUseCase
import com.example.domain.usecases.SaveLoadPagesUseCase
import com.example.domain.usecases.SaveTimeUpdateUseCase
import com.example.domain.usecases.UpdateToFavoriteMoviesUseCase
import dagger.Module
import dagger.Provides

@Module
class MoviesUseCaseModule {

    @Provides
    fun provideGetPopularMoviesUseCase(movieRepository: MovieRepository): GetPopularMoviesUseCase {
        return GetPopularMoviesUseCase(movieRepository)
    }

    @Provides
    fun provideGetMovieByIdUseCase(movieRepository: MovieRepository): GetMovieByIdUseCase {
        return GetMovieByIdUseCase(movieRepository)
    }

    @Provides
    fun provideGetMoviesFromDbUseCase(movieDatabaseRepository: MovieDatabaseRepository): GetMoviesFromDbUseCase {
        return GetMoviesFromDbUseCase(movieDatabaseRepository)
    }

    @Provides
    fun provideInsertMoviesUseCase(movieDatabaseRepository: MovieDatabaseRepository): InsertMoviesUseCase {
        return InsertMoviesUseCase(movieDatabaseRepository)
    }

    @Provides
    fun provideRemoveFavoriteMovieByIdUseCase(movieDatabaseRepository: MovieDatabaseRepository): RemoveFavoriteMovieByIdUseCase {
        return RemoveFavoriteMovieByIdUseCase(movieDatabaseRepository)
    }

    @Provides
    fun provideRemovePopularMoviesUseCase(movieDatabaseRepository: MovieDatabaseRepository): RemovePopularMoviesUseCase {
        return RemovePopularMoviesUseCase(movieDatabaseRepository)
    }

    @Provides
    fun provideUpdateFavoriteMoviesUseCase(movieDatabaseRepository: MovieDatabaseRepository): UpdateToFavoriteMoviesUseCase {
        return UpdateToFavoriteMoviesUseCase(movieDatabaseRepository)
    }

    @Provides
    fun provideGetTimeUpdateUseCase(appInfoRepository: AppInfoRepository): GetTimeUpdateUseCase {
        return GetTimeUpdateUseCase(appInfoRepository)
    }

    @Provides
    fun provideSaveTimeUpdateUseCase(appInfoRepository: AppInfoRepository): SaveTimeUpdateUseCase {
        return SaveTimeUpdateUseCase(appInfoRepository)
    }

    @Provides
    fun provideGetLoadPagesUseCase(appInfoRepository: AppInfoRepository): GetLoadPagesUseCase {
        return GetLoadPagesUseCase(appInfoRepository)
    }

    @Provides
    fun provideSaveLoadPagesUseCase(appInfoRepository: AppInfoRepository): SaveLoadPagesUseCase {
        return SaveLoadPagesUseCase(appInfoRepository)
    }
}