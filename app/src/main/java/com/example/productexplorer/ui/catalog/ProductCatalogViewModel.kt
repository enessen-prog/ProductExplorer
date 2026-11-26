package com.example.productexplorer.ui.catalog

import androidx.lifecycle.ViewModel
import com.example.productexplorer.data.ProductUi
import com.example.productexplorer.data.sampleCategories
import com.example.productexplorer.data.sampleProducts
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProductCatalogViewModel : ViewModel() {
    private val allProducts: List<ProductUi> = sampleProducts()
    private val allCategories: List<String> = sampleCategories()

    private val _uiState = MutableStateFlow(
        ProductCatalogUiState(
            products = allProducts,
            categories = allCategories
        )
    )

    val uiState: StateFlow<ProductCatalogUiState> = _uiState.asStateFlow()

    private fun filterProducts(state: ProductCatalogUiState): List<ProductUi> {
        val matchingProducts = allProducts.filter { product ->
            val matchesSearch =
                product.title.contains(state.searchQuery, ignoreCase = true) ||
                        product.brand.contains(state.searchQuery, ignoreCase = true) ||
                        product.category.contains(state.searchQuery, ignoreCase = true)
            val matchesStock =
                !state.showOnlyInStock || product.stock > 0
            val matchesCategory =
                state.selectedCategory == null || product.category == state.selectedCategory
            val matchesFavorites =
                !state.showOnlyFavorites || state.favoriteProductIds.contains(product.id)
            matchesSearch && matchesStock && matchesCategory && matchesFavorites
        }

        return when (state.priceSortOrder) {
            PriceSortOrder.NONE -> matchingProducts
            PriceSortOrder.ASCENDING -> matchingProducts.sortedBy { product -> product.price }
            PriceSortOrder.DESCENDING -> matchingProducts.sortedByDescending { product -> product.price }
        }
    }

    private fun ProductCatalogUiState.withFilteredProducts(): ProductCatalogUiState {
        return copy(products = filterProducts(this))
    }

    fun onSearchQueryChange(newValue: String) {
        _uiState.update { currentState ->
            currentState.copy(
                searchQuery = newValue
            ).withFilteredProducts()
        }
    }

    fun onToggleStockFilter() {
        _uiState.update { currentState ->
            currentState.copy(
                showOnlyInStock = !currentState.showOnlyInStock
            ).withFilteredProducts()
        }
    }

    fun onFavoriteClick(productId: Int) {
        _uiState.update { currentState ->
            val newFavoriteIds =
                if (currentState.favoriteProductIds.contains(productId)) {
                    currentState.favoriteProductIds - productId
                } else {
                    currentState.favoriteProductIds + productId
                }

            currentState.copy(
                favoriteProductIds = newFavoriteIds
            ).withFilteredProducts()
        }
    }

    fun onCategoryClick(category: String) {
        _uiState.update { currentState ->
            val newSelectedCategory =
                if (currentState.selectedCategory == category) {
                    null
                } else {
                    category
                }

            currentState.copy(
                selectedCategory = newSelectedCategory
            ).withFilteredProducts()
        }
    }

    fun onToggleFavoritesFilter() {
        _uiState.update { currentState ->
            currentState.copy(
                showOnlyFavorites = !currentState.showOnlyFavorites
            ).withFilteredProducts()
        }
    }

    fun onPriceSortClick() {
        _uiState.update { currentState ->
            val newPriceSortOrder = when (currentState.priceSortOrder) {
                PriceSortOrder.NONE -> PriceSortOrder.ASCENDING
                PriceSortOrder.ASCENDING -> PriceSortOrder.DESCENDING
                PriceSortOrder.DESCENDING -> PriceSortOrder.NONE
            }

            currentState.copy(
                priceSortOrder = newPriceSortOrder
            ).withFilteredProducts()
        }
    }

    fun onResetFilters() {
        _uiState.update { currentState ->
            currentState.copy(
                searchQuery = "",
                showOnlyInStock = false,
                selectedCategory = null,
                showOnlyFavorites = false,
                priceSortOrder = PriceSortOrder.NONE
            ).withFilteredProducts()
        }
    }
}
