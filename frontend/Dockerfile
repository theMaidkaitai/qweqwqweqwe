FROM node:20-alpine AS build

WORKDIR /app

COPY package.json package-lock.json ./

RUN npm ci

COPY . .

RUN npm run build

RUN echo "=== Проверка сборки ==="
RUN ls -la /app
RUN find /app -name "dist" -type d 2>/dev/null || echo "dist не найден, ищу build..."
RUN find /app -name "build" -type d 2>/dev/null || echo "build не найден"

FROM nginx:stable-alpine

COPY --from=build /app/dist /usr/share/nginx/html 2>/dev/null || \
  COPY --from=build /app/build /usr/share/nginx/html/

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]