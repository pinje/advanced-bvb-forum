FROM node:22-alpine as build
WORKDIR /app/src
COPY package*.json ./
RUN npm ci
COPY . ./
RUN npm run build

FROM node:22-alpine
WORKDIR /usr/app
COPY --from=build /app/src/dist/frontend/server ./
CMD node server.mjs
EXPOSE 80
