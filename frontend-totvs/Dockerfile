FROM node:12.14-alpine as build-step
RUN mkdir -p /app
WORKDIR /app
COPY package.json /app
RUN npm install --silent
COPY . .
RUN npm run build --prod

# Stage 2
FROM nginx:1.17.1-alpine
VOLUME /var/cache/nginx
COPY --from=build-step app/dist/frontend-totvs/* /usr/share/nginx/html
EXPOSE 80