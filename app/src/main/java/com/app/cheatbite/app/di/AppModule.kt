package com.app.cheatbite.app.di

import android.content.Context
import androidx.credentials.CredentialManager
import com.app.cheatbite.core.data.networking.HttpClientFactory
import com.app.cheatbite.core.data.remote.source.SignInWithGoogleDataSource
import com.app.cheatbite.R
import com.app.cheatbite.UserSharedPreference
import com.app.cheatbite.app.domain.repository.AuthRepository
import com.app.cheatbite.app.domain.repository.AuthRepositoryImpl
import com.app.cheatbite.app.domain.repository.SnackSenseiRepository
import com.app.cheatbite.app.domain.repository.SnackSenseiRepositoryImpl
import com.app.cheatbite.app.domain.repository.UserDataRepository
import com.app.cheatbite.app.domain.repository.UserDataRepositoryImpl
import com.app.cheatbite.app.domain.usecase.ObserveCurrentUserUseCase
import com.app.cheatbite.app.domain.usecase.SignInWithGoogleUseCase
import com.app.cheatbite.app.domain.usecase.SnackSenseiUseCase
import com.app.cheatbite.app.domain.usecase.UserDataUseCase
import com.app.cheatbite.app.viewmodels.AccountViewModel
import com.app.cheatbite.app.viewmodels.AuthViewModel
import com.app.cheatbite.app.viewmodels.MainViewModel
import com.app.cheatbite.core.data.local.source.LocalSnackSenseiDataSource
import com.app.cheatbite.core.data.local.source.SnackSenseiDataSource
import com.app.cheatbite.core.data.remote.source.FirestoreDataSource
import com.app.cheatbite.core.data.remote.source.RemoteFirestoreDataSource
import com.app.cheatbite.core.data.remote.source.RemoteSignInWithGoogleDataSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.ktor.client.engine.cio.CIO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module


val appModule = module {
    single { androidContext().getSharedPreferences("user_preferences", Context.MODE_PRIVATE) }
    single<CoroutineDispatcher>(named("IODispatcher")) { Dispatchers.IO }
    single<CoroutineDispatcher>(named("DefaultDispatcher")) { Dispatchers.Default }
    single { HttpClientFactory.create(CIO.create()) }
    single { UserSharedPreference(get()) }
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { androidContext().getString(R.string.default_web_client_id) }
    single { CredentialManager.create(androidContext()) }
    singleOf(::RemoteSignInWithGoogleDataSource).bind<SignInWithGoogleDataSource>()
    singleOf(::LocalSnackSenseiDataSource).bind<SnackSenseiDataSource>()
    singleOf(:: RemoteFirestoreDataSource).bind<FirestoreDataSource>()
    single<AuthRepository> { AuthRepositoryImpl(remoteSignInWithGoogleDataSource = get(), defaultDispatcher = get(named("IODispatcher"))) }
    single<SnackSenseiRepository> { SnackSenseiRepositoryImpl( context = androidContext(), localDataSource = get(),defaultDispatcher = get(named("IODispatcher"))) }
    single<UserDataRepository> { UserDataRepositoryImpl(defaultDispatcher = get(named("IODispatcher")), remoteFirestoreDataSource = get(), context = get()) }
    factory { SignInWithGoogleUseCase(get()) }
    factory { SnackSenseiUseCase(get()) }
    factory { UserDataUseCase(get()) }
    single { ObserveCurrentUserUseCase(get()) }
    viewModel { MainViewModel(get(),get(),get(), get()) }
    viewModel { AccountViewModel(get(), get(), get(), get(), get()) }
    viewModel { AuthViewModel(get(), get(), get()) }
}