# Use the official Node.js image as the base image for building the app
FROM node:22-alpine AS build

# Set the working directory
WORKDIR /app

# Copy package.json and package-lock.json
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application code
COPY . .

# Build the Angular application
RUN npm run build -- --configuration production

# Use the official Nginx image as the base image for serving the app
FROM nginx:latest

# Copy the custom Nginx configuration file
COPY nginx.conf /etc/nginx/nginx.conf

# Clear cache
RUN rm -rf /usr/share/nginx/html/*

# Copy the built Angular application from the previous stage to the Nginx html folder
COPY --from=build /app/dist/frontend/browser/**/* /usr/share/nginx/html/

# Expose port 80
EXPOSE 80

# Start Nginx server
CMD ["nginx", "-g", "daemon off;"]