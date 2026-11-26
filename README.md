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
- TP8 : catalogue interactif
- TP9 : State Hoisting
- TP10 : ViewModel

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

## TP8 - Catalogue interactif

J'ai rendu le catalogue interactif avec les états Compose (mutableStateOf, remember et rememberSaveable).

- un champ de recherche qui filtre les produits par titre, marque ou catégorie
- un bouton pour afficher uniquement les produits en stock
- le nombre de produits affichés, qui se met à jour tout seul
- un bouton sur chaque carte pour ajouter ou retirer un produit des favoris

La liste filtrée est calculée avec remember et ne change que si la recherche ou le filtre change. La recherche, le filtre et les favoris sont gardés avec rememberSaveable, donc ils restent même quand on tourne l'écran.

## TP9 - State Hoisting

J'ai réorganisé le catalogue en séparant l'état de l'affichage :

- ProductCatalogContainer (stateful) garde la recherche, les filtres et les favoris, calcule la liste filtrée et contient toggleFavorite
- ProductCatalogScreen (stateless) reçoit les valeurs en paramètres et fait remonter les clics par des callbacks
- MainActivity appelle maintenant ProductCatalogContainer, et les catégories sont passées en paramètre

Les données descendent et les événements remontent. Les previews du catalogue utilisent maintenant le container (thème clair et thème sombre), et j'ai ajouté une preview de l'écran stateless avec des valeurs fixes.

### Fonctionnalités en plus

J'ai ajouté ces fonctionnalités moi-même, en gardant le même principe (l'état est dans le container, l'écran reçoit les valeurs et les callbacks) :

- les pastilles de catégories sont cliquables et filtrent les produits ; la catégorie choisie change de couleur et un deuxième clic enlève le filtre
- un filtre "Favoris uniquement" pour n'afficher que les produits ajoutés aux favoris
- un tri par prix qui passe de "Prix croissant" à "Prix décroissant" puis revient à l'ordre de départ
- une croix dans le champ de recherche pour l'effacer d'un coup
- un message quand aucun produit ne correspond aux filtres

Tous ces états sont gardés avec rememberSaveable, comme la recherche.

## TP10 - ViewModel

J'ai déplacé l'état du catalogue dans un ViewModel (ProductCatalogViewModel) :

- le ViewModel garde les produits, les catégories, la recherche, le filtre stock et les favoris avec mutableStateOf et private set, donc l'écran peut lire ces valeurs mais pas les modifier directement
- les actions passent par des fonctions du ViewModel (onSearchQueryChange, onToggleStockFilter, onFavoriteClick)
- la liste filtrée est calculée dans le ViewModel
- ProductCatalogContainer récupère le ViewModel avec viewModel() et transmet ses valeurs à ProductCatalogScreen, qui n'a pas changé
- MainActivity n'a plus besoin de passer les produits et les catégories

J'ai ajouté la dépendance lifecycle-viewmodel-compose dans le Version Catalog. Les previews affichent directement l'écran stateless, pour ne pas dépendre du ViewModel. L'état reste maintenant après une rotation de l'écran.

Les fonctionnalités en plus du TP9 (catégories, favoris uniquement, tri par prix) sont aussi passées dans le ViewModel.

### Fonctionnalités en plus

- un bouton "Réinitialiser les filtres" qui remet la recherche, le filtre stock, la catégorie, les favoris uniquement et le tri à zéro d'un coup ; il n'apparaît que si un filtre est actif, et c'est le ViewModel qui le calcule (hasActiveFilters) et qui fait la remise à zéro (onResetFilters)
- le nombre de favoris affiché à côté du nombre de produits
