# Product Explorer

Application Android en Kotlin avec Jetpack Compose que je fais pendant les TP du cours de développement Android avec Jetpack Compose.

L'appli affiche un petit catalogue de produits et je l'ai complétée à chaque TP.

## Technologies

- Kotlin
- Jetpack Compose
- Material 3
- Gradle (Kotlin DSL)

## Lancer le projet

1. Cloner le dépôt : `git clone https://github.com/enessen-prog/ProductExplorer.git`
2. Ouvrir le dossier dans Android Studio
3. Attendre la synchro Gradle
4. Lancer l'appli sur un émulateur ou un téléphone

## TP faits

- TP4 : écran de détail produit
- TP5 : écran d'accueil
- TP6 : catalogue de produits
- TP7 : personnalisation graphique

## TP4 - Écran de détail produit

J'ai fait la fiche d'un produit avec des données mises directement dans le code : image, titre, marque, catégorie, prix, remise, note, stock, livraison, description, garantie et un bouton "Ajouter au panier".

J'ai utilisé Text, Button, Icon, Image, Card, OutlinedTextField, Column et Row.

J'ai découpé l'écran en plusieurs petits composables (ProductDetailScreen, ProductImage, ProductHeader, ProductPriceCard, ProductRating, ProductAvailabilityCard, ProductDescription, ProductWarrantyField, AddToCartButton). Chacun reçoit ses données en paramètres et a un `modifier: Modifier = Modifier`, et le clic du bouton remonte par un callback.

Il y a deux previews : un produit en stock et un produit en rupture de stock.

## TP5 - Écran d'accueil

J'ai ajouté un écran d'accueil (ProductHomeScreen) qui s'affiche au lancement. Il est fait avec Column, Row, Box, Spacer et Surface.

Il contient un en-tête, une fausse barre de recherche, un produit mis en avant avec son prix, sa note et son stock, une liste de catégories et un bloc "Offre du jour" avec un badge "Nouveau" placé en haut à droite grâce à Box.

## TP6 - Catalogue de produits

J'ai ajouté l'écran ProductCatalogScreen qui affiche une liste de produits avec LazyColumn. Les catégories sont affichées en ligne avec LazyRow.

J'ai ajouté un id dans ProductUi pour pouvoir l'utiliser comme key dans items. Les produits viennent de sampleProducts() et les catégories de sampleCategories(). Chaque produit est affiché avec la même carte (ProductListItem).

## TP7 - Personnalisation graphique

J'ai personnalisé le thème Material 3 dans Theme.kt et Type.kt :

- une palette claire et une palette sombre, choisies selon le mode du téléphone
- une typographie (ProductTypography)
- des formes avec des arrondis (ProductShapes)

Le fond, le titre, les cartes et les catégories du catalogue utilisent maintenant les couleurs et les styles du thème. J'ai aussi ajouté une preview en thème clair et une en thème sombre pour comparer.
