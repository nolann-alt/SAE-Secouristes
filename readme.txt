"Commande de compilation pour l'appli" :
javac -d ../class/ --module-path ../lib/javafx --add-modules javafx.controls,javafx.fxml ../src/**/*.java && cp  ../src/vue/SecouristeView.fxml ../class/vue/ && cp ../src/vue/styles.ressources.css ../class/vue/ && mkdir -p ../class/vue/img && cp ../src/vue/img/*.png ../class/vue/img/ && mkdir -p ../class/vue/fonts &&  cp ../src/vue/fonts/*.otf ../class/vue/fonts/


"Commande d'execution pour l'appli" :
java --module-path ../lib/javafx --add-modules javafx.controls,javafx.fxml -cp ../class vue.SecouristeView

/* Commande de complation au propre */
javac -d ../class/ --module-path ../lib/javafx --add-modules javafx.controls,javafx.fxml ../src/**/*.java && \
cp ../src/vue/SecouristeView.fxml ../class/vue/ && \
cp ../src/vue/styles.ressources.css ../class/vue/ && \
mkdir -p ../class/vue/img && cp ../src/vue/img/*.png ../class/vue/img/ && \
mkdir -p ../class/vue/fonts && cp ../src/vue/fonts/*.otf ../class/vue/fonts/

/* Commande pour compiler juste le package vue */
javac -d ../class/ --module-path ../lib/javafx --add-modules javafx.controls,javafx.fxml ../src/vue/*.java && cp  ../src/vue/SecouristeView.fxml ../class/vue/ && cp ../src/vue/styles.ressources.css ../class/vue/ && mkdir -p ../class/vue/img && cp ../src/vue/img/*.png ../class/vue/img/ && mkdir -p ../class/vue/fonts &&  cp ../src/vue/fonts/*.otf ../class/vue/fonts/

/* Commande pour compiler le controller et la vue */
javac -d ../class/ \
  --module-path ../lib/javafx \
  --add-modules javafx.controls,javafx.fxml \
  ../src/vue/*.java ../src/controller/*.java && \
cp ../src/vue/*.fxml ../class/vue/ && \
cp ../src/vue/styles.ressources.css ../class/vue/ && \
mkdir -p ../class/vue/img && cp ../src/vue/img/*.png ../class/vue/img/ && \
mkdir -p ../class/vue/fonts && cp ../src/vue/fonts/*.otf ../class/vue/fonts/

