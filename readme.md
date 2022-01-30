# Rapport

## Introduction

L'objectif de ce projet et de réaliser un mini jeu inspiré du jeu mobile *"flappy bird"*. Dans notre version, un cercle se déplace le long d'une ligne brisé, le but étant de garder le cercle sur cette ligne.
Pour cela, le joueur doit cliquer avec la souris ou sur la touche espace afin de faire sauter le cercle, celui-ci étant soumis à la gravité, il redescend tout seul.

![screnshot.png](C:\Users\0sh8t\Code\PCII\Rapport\images_rapport\screnshot.png)

## Analyse globale

Les 4 fonctionnalités majeures de notre jeu sont les suivantes :

1. La fenêtre d'affichage avec le dessin du cercle et de la ligne brisée
2. Le défilement automatique de la ligne brisée
3. La réaction du cercle aux actions de l'utilisateur
4. La fin du jeu lorsque le cercle n'est plus sur la ligne brisée

Ces 3 fonctionnalités majeures consistent en une série de plus petites fonctionnalités, voici la liste détaillée des implémentations de ces plus "petites" fonctionnalités.

- Création de la fenêtre d'affichage
- Dessin du cercle
- Saut du cercle lorsque l'utilisateur clique sur la souris
- Chute du cercle
- Génération des points de la ligne brisée
- Dessin de la ligne brisée
- Animation de la ligne brisée
- Terminaison du jeu

En plus de ces fonctionnalités nécessaires au fonctionnement du jeu, on peut aussi faire quelques ajouts afin d'améliorer le jeu:

- Saut lorsque l'utilisateur clique sur la barre espace
- Accélération lors de la chute
- Affichage des éléments du décor (tuyau, oiseaux, ...)
- Animation des éléments du décor
- Affichage d'un oiseau à la place d'un cercle
- Animation de cet oiseau en fonction de s'il saute ou tombe.

## Plan de développement

![digramme.png](C:\Users\0sh8t\Code\PCII\Rapport\images_rapport\digramme.png)

## Conception générale

Afin de simplifier et rendre plus claire l'implémentation, le modèle MVC a été choisis.

<img src="file:///C:/Users/0sh8t/Code/PCII/Rapport/images_rapport/mvc.png" title="" alt="mvc.png" width="554">

Voici comment les différentes fonctionnalités du jeu rentre dans ce motif :

- Modèle `Engine.java`
  - Modification de la hauteur du cercle lorsque l'utilisateur click sur sa souris
- Vue `Display.java`
  - Dessin du cercle
- Contrôleur `Controller.java`
  - Détection du click de l'utilisateur sur sa souris pour faire sauter le cercle

## Conception détaillée

###### Fenêtre d'affichage

Pour créer la fenêtre, nous utilisions l'API Swing. Nous avons créé une classe `Window` qui étend la classe `JFrame` de Swing afin de simplifier la création d'une fenêtre d'affichage (et rendre plus propre et compact le code). Cette classe à aussi une fonction `initWindow` qui permet d'ajouter un affichage à la fenêtre, un affichage étant une instance de la classe `Display` qui est détailler ci-dessous.

![window.png](C:\Users\0sh8t\Code\PCII\Rapport\images_rapport\window.png)

###### Affichage des éléments graphiques

La classe `Display` correspond à notre affichage, cette classe étends la classe `JPanel` de l'API Swing. La fonction `paint` issu de la classe `JPanel` nous permet de dessiner des choses à l'écran. On peut alors par exemple grâce à la fonction `drawOval` dessiner le cercle.

![display.png](C:\Users\0sh8t\Code\PCII\Rapport\images_rapport\display.png)

###### Déplacement de l'ovale

Pour gérer les cliques souris de l'utilisateur, nous utilisons la programmation événementielle. Nous avons créé une classe `Controller` qui implémente l'interface `mouseListener`. Cette interface intègre la fonction `mouseClicked` qui est appelé lorsque l'utilisateur click sur un des boutons de sa souris en particulier dans notre cas lorsqu'il clique sur l'affichage. Plus tard, lors du développement, nous avons ajouté la possibilité d'utiliser la touche espace pour faire sauter le cercle, cela est toujours fait via la classe `Controller` qui implémente aussi l'interface `KeyListener` qui permet de détecter lorsque l'utilisateur appuie sur une touche via la fonction `Keypressed`.

Lorsque la fonction `mouseClicked` ou `Keypressed(barre espace)` est appelé, la valeur de la position verticale du cercle est incrémenté de manière à le faire sauter.

![controller.png](C:\Users\0sh8t\Code\PCII\Rapport\images_rapport\controller.png)

###### Mécanisme de gravité

La gravité est gérée grâce via la classe `Fly` qui étends `Thread`, la classe `Thread` implémente la fonction `run` qui est appelé lorsque le thread est démarré. Ce thread en particulier appel tous les 100 ms la fonction `moveDown` de `Engine` qui fait descendre le cercle en décrémentant la valeur de la position verticale du cercle.

![fly.png](C:\Users\0sh8t\Code\PCII\Rapport\images_rapport\fly.png)

###### Génération de la ligne brisée

La ligne brisée est générée via la classe `Path`, le parcours est représenté par une `ArrayList` de points nommé `path`. Ces points sont créés via le constructeur de la classe en fonction de la largeur de l'affichage de manière à ce que le dernier point soit hors de l'affichage, cela permettant à ligne brisé d'être dessiné sur l'ensemble de l'affichage ([pseudo-code](#algorithme-de-generation-du-parcours)). Afin de s'assurer que 2 points voisins n'aient pas des positions verticales trop différentes, on génère la position verticale d'un nouveau point en fonction de la hauteur du point précédent. la différence maximum de hauteur entre les 2 points est calculé via cette [formule](#calcul-de-la-distance-verticale-maximum-entre-2-points). Cette formule s'assure que la pente entre 2 points ne soit pas trop raide.

Pour afficher une ligne brisée, on dessine grâce à la fonction `paint` de l'affichage`Display` des droites entre chaque point de`path` via la fonction `drawLine` de Swing.

![path.png](C:\Users\0sh8t\Code\PCII\Rapport\images_rapport\path.png)

###### Animation de la ligne brisée

L'animation de la ligne brisée est faite via la classe `Forward` qui étends elle aussi la classe `Thread`. Ce thread a deux missions :

1. Il "fait avancer" l'affichage tous les 100 ms en incrémentant la variable `currentX` qui correspond au mouvement de l'affichage. Pour animer la ligne brisée, on la redessine les points à leur position - `currentX` ce qui a pour effet de "faire reculer" la ligne brisée.

2. Il met à jour la variable `path` qui correspond à la liste des points de la ligne brisée. Lorsque qu'un point sort de l'affichage, il est supprimé de la liste des points, de la même manière, lorsque le dernier point rentre dans la fenêtre d'affichage, un nouveau est générer plus loin hors de la fenêtre d'affichage ([pseudo-code](#algorithme-de-mise-a-jour-du-parcours)).

![forward.png](C:\Users\0sh8t\Code\PCII\Rapport\images_rapport\forward.png)

###### Détection des collisions

La détection de collision se fait via la fonction `testPerdu` de la classe `Engine`, celle-ci est appelé après chaque modification de la hauteur du cercle à savoir à chaque fois qu'il tombe ou qu'il saute. Cette fonction vérifie que le point le plus haut du cercle est au-dessus de la ligne brisée et que le point le plus bas du cercle est en dessous de la ligne. ([pseudo-code](#algorithme-de-detection-de-collisions)).

À cause du fait qu'au début du jeu le premier point du parcours n'est pas à l'extrémité de la fenêtre d'affichage, l'indice des point du parcours définissant la droite sur laquelle se trouve le cercle à l'instant t n'est pas toujours le même, il a donc fallu gérer ce cas particulier.

###### Affichage et animation des oiseaux

Les oiseaux sont des instances de la classe `Bird` celle-ci implémente l'interface `Thread`. Chaque oiseau a un attribut `delay` qui représente la fréquence à laquelle on met à jour la position ainsi que l'état de l'oiseau. Afin de créer l'animation de battement des ailes, on utilise 8 images différentes, l'état de l'oiseau est donc un entier entre 0 et 7 qui représente l'image à dessiner.

Tous les oiseaux affichés à l'écran sont stockés dans une `ArrayList`, une fois qu'un oiseau est sorti de l'affichage, il est supprimé de la liste et son Thread est arrêter.

Toutes les secondes, il y a une chance sur dix qu'un nouvel oiseau apparaisse.

![bird.png](C:\Users\0sh8t\Code\PCII\Rapport\images_rapport\bird.png)

###### Synchronisation de l'affichage

L'affichage est rafraîchi 24 fois par seconde si besoin, c'est-à-dire que lorsque la vue a besoin d'être redessiné après par exemple que l'utilisateur ai appuyé sur la barre espace, alors le `DispalayUpdater` sera notifier et redessinera l'affichage sinon l'affichage ne sera pas redessiner ce qui permet d'économiser des mises à jour d'affichage inutiles et donc des ressources.

### Algorithmes

###### Définition des constantes utilisées dans les pseudo codes suivants

```
HAUTEUR_MAX : hauteur maximum entre 2 points
DISTANCE : distance horizontle entre 2 points
VITESSE : distance parcourut par le cercle par seconde
VITESSE_CHUTE :  hauteur de la chute du cercle par seconde
```

###### Calcul de la distance verticale maximum entre 2 points

```
HAUTEUR_MAX = ( DISTANCE / VITESSE ) * VITESSE_CHUTE
```

###### Algorithme de génération du parcours

```
ajouter un point à la position initiale du cercle

tant que x < fin de la fenêtre d'affichage + DISTANCE : 
    
    p = le dernier point du parcours

    si p.y est trop grand alors 
        y = p.y - un nombre aléatoire entre 0 et HAUTEUR_MAX
    sinon si p.y est trop petit alors
        y = p.y + un nombre aléatoire entre 0 et HAUTEUR_MAX
    sinon
        y = un nombre aléatoire entre 0 et HAUTEUR_MAX - HAUTEUR_MAX/2

    ajouter le point de coordonné (x,y) au parcours

    x += DISTANCE
```

###### Algorithme de mise à jour du parcours

```
Si le dernier point du parcours est entrée dans la fenêtre d'affichage alors
    
    x = abscisse du dernier point du parcours + DISTANCE
    
    // y est calculé de la même manière que dans l'algo ci dessus (si trop haut, trop bas, ...)
    y = un nombre aléatoire entre 0 et HAUTEUR_MAX
    
    ajouter le point de coordonné (x,y) au parcours


Si le deuxième point du parcours est sortie de la fenêtre d'affichage alors
    retier le premier point du parcours de la liste corrspondant au parcours
```

###### Calcul de la valeur de l’ordonnée (y) sur une droite définit par les points A et B au point d’abscisse x

```
A.x, A.y, B.x, B.y : respectivement l'abscisse et l'ordonnée des points A et B

PENTE = (B.y - A.y) / (B.x - A.x)
y = A.y + ( PENTE * ( x - A.x) )
```

###### Algorithme de détection de collisions

```
x : position horizontale du cercle

A : permier point de la liste
B : deuxieme point de la liste

// Utilisation de la formule précédente
yChemin = hauteur de la droite définit par les point A et B au point d'abscisse x

Si le haut du cercle < yChemin OU le bas du cercle > yChemin alors
    Perdu
```

## Résultat

![result.png](C:\Users\0sh8t\Code\PCII\Rapport\images_rapport\result.png)

## Documentation utilisateur

- Prérequis : Java 11 ou supérieur

- Mode d'emploi : pour lancer l'application, il suffit de double-cliquer dessus ou d'utiliser la commande suivante dans un terminal : `java -jar flappy.jar`

- Pour faire sauter le cercle, vous pouvez cliquer avec votre souris ou appuyer sur votre barre espace.

## Documentation développeur

Comme précisé précédemment le modèle MVC a été utilisé pour ce projet, vous retrouvez donc 3 packages nommés `Control` , `Engine` et `View`. La méthode main est quant à elle à part, hors de tout package.

Les constantes liées à l'affichage se trouvent dans la classe `Display` notamment la taille de la fenêtre. Les constantes liées au gameplay se trouvent quant à elles dans la classe `Engine`. On y retrouve par exemple la vitesse de déplacement et de chute du cercle ainsi que la hauteur des sauts, ...

Les classes les plus importantes sont `Engine` et `Display`, ce sont sans doute les seules classes que vous aurez besoin de modifier si vous souhaitez ajouter des fonctionnalités. La classe `Engine` est responsable de toute la mécanique du jeu donc si vous voulez rajouter des mécaniques, c'est à cette classe qu'il faudra ajouter votre fonction. De l'autre côté, si vous voulez ajouter des éléments graphiques, il vous suffira de créer une nouvelle classe dans le package `View` et de modifier la fonction `paint` de `Display` afin d'afficher vos ajouts.

Les ajoutes potentielles :

- Afficher des tuyaux à la place d'une ligne brisée, pour ça, il suffit de créer une nouvelle classe `VueTuyau`. Cette classe aurait un fonction `drawPipeUp` et `drawPipeDown`. Il suffirait d'appeler chacune de ces fonctions pour chaque point dans la fonction `paint` de `Dispaly` au lieu de `drawLine`.

- Dessiner un vrai flappy bird à la place du cercle. Un nouvelle classe `VueFlappy `aurait été nécessaire ainsi qu'une fonction `drawFlappy` qui remplacerait `drawOval` dans le `paint `de `Display`. On pourrait aussi avoir dans cette classe un variable `inclinaison` qui sera mis à jour en fonction de la distance de la chute de flappy. En fonction de cette variable, on dessinait flappy avec une certaine inclinaison.

- Et biens d'autres, à vous de les imaginer

## Conclusions et perspectives

Les fonctionnalités nécessaires ont bien été implémentées, et même un petit peu plus.

La principale difficulté a été la communication entre toutes les parties du modèle MVC, la solution finale est loin d'être la plus propre est nécessite d'être améliorée si le jeu devait partir en production.

La création de ce jeu m'a permis de réutiliser l'API Swing dont je ne m'étais pas servi depuis un certain temps et de redécouvrir l'utilisation des Threads.

Si j'avais eu plus de temps, j'aurais sans doute amélioré la partie graphique du Jeu comme mentionné dans la partie précédente, ces ajouts n'étant pas les plus compliqués à faire, ça aurait été largement envisageable. Ce qui aurait vraiment été intéressant d'ajouter, c'est un système de menu permettant de relancer une partie une fois perdu et un système à 2 joueurs (un clavier et un souris).

Ce projet m'a permis de me rendre compte de l'importance du rapport ainsi que le temps nécessaire à sa rédaction. C'était un bon entraînement au vu du projet que nous allons devoir réaliser lors des prochaines semaines.