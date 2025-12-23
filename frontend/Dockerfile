FROM node:20-alpine AS build

WORKDIR /app  # ← ДОБАВЛЕНО: Рабочая директория

COPY package.json package-lock.json ./
RUN npm ci --silent  # ← Лучше чем npm install для production

COPY . .
RUN npm run build

FROM nginx:stable-alpine

COPY --from=build /app/dist /usr/share/nginx/html  


EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]