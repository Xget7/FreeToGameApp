@file:Suppress("UNCHECKED_CAST")

package dev.xget.freetogame.presentation.utils

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.ViewModelFactoryDsl
import androidx.savedstate.SavedStateRegistryOwner

/**
 * Creates a ViewModelProvider.Factory for creating ViewModel instances.
 *
 * This utility function allows you to define custom ViewModel initialization logic
 * using a lambda, making it easier to pass dependencies to ViewModel constructors
 * without setting up a full dependency injection framework.
 *
 * @param Vm The type of ViewModel being created, constrained to subclasses of ViewModel.
 * @param initializer A lambda function that defines how to create an instance of the ViewModel.
 * @return An instance of ViewModelProvider.Factory that uses the initializer lambda to create ViewModels.
 *
 */
fun <Vm : ViewModel> viewModelFactory(
    defaultArgs: Bundle? = null,
    owner: SavedStateRegistryOwner,
    initializer: (handle: SavedStateHandle) -> Vm
): ViewModelProvider.Factory {
    return object : AbstractSavedStateViewModelFactory(owner, defaultArgs = defaultArgs) {
        override fun <T : ViewModel> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T  {
            return initializer(handle) as T
        }
    }
}