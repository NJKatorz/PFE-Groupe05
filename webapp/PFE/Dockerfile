# Image contenant Node.js et npm pour construire l'application
FROM node:lts-alpine as build-stage
# Répertoire de travail
WORKDIR /app
# Copie des fichiers package.json et package-lock.json pour npm
COPY package*.json ./
# Installation des dépendances
RUN npm install
# Copie des fichiers sources
COPY . .
# Construction de l'application
RUN npm run build

# Image contenant Nginx pour exécuter l'application
FROM nginx:stable-alpine as production-stage
# Copie le contenu du répertoire dist dans le répertoire de travail de Nginx
COPY --from=build-stage /app/dist /usr/share/nginx/html
# Exposition du port 80
EXPOSE 80
# Commande pour exécuter l'application en démarrage Nginx
CMD ["nginx", "-g", "daemon off;"]