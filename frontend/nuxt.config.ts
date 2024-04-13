// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  devtools: { enabled: true },
  modules: ["@nuxt/ui"],
  css: ["~/assets/css/main.css"],
  runtimeConfig: {
    public: {
      serverIp: process.env.NUXT_PUBLIC_SERVER_IP,
    },
  },
  devServer: {
    host: "0.0.0.0",
  },
});
