FROM node:20-alpine AS build

WORKDIR /app
RUN npm install -g typescript


COPY package.json package-lock.json ./

RUN npm ci
COPY . .
RUN npm run build



FROM nginx:stable-alpine

COPY --from=build /app/dist /usr/share/nginx/html 2>/dev/null || \
  COPY --from=build /app/build /usr/share/nginx/html/

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]