FROM node:18-alpine

WORKDIR /app

COPY package*.json ./

RUN npm install

COPY . .

RUN npm run build

FROM nginx:alpine

COPY --from=0 /app/dist /usr/share/nginx/html

ENV HOST=0.0.0.0
ENV PORT=1234

EXPOSE 1234

CMD ["nginx", "-g", "daemon off;"]