package com.example.productexplorer.ui.catalog

import com.example.productexplorer.data.ProductUi

enum class PriceSortOrder {
    NONE,
    ASCENDING,
    DESCENDING
}

data class ProductCatalogUiState(
    val products: List<ProductUi> = emptyList(),
    val categories: List<String> = emptyList(),
    val searchQuery: String = "",
    val showOnlyInStock: Boolean = false,
    val favoriteProductIds: List<Int> = emptyList(),
    val selectedCategory: String? = null,
    val showOnlyFavorites: Boolean = false,
    val priceSortOrder: PriceSortOrder = PriceSortOrder.NONE
) {
    val hasActiveFilters: Boolean
        get() = searchQuery.isNotEmpty() ||
                showOnlyInStock ||
                selectedCategory != null ||
                showOnlyFavorites ||
                priceSortOrder != PriceSortOrder.NONE
}
