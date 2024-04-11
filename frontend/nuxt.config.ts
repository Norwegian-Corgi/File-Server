// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  devtools: { enabled: true },
  modules: ["@element-plus/nuxt"],
  css: ["~/assets/css/main.css"],
  elementPlus: {
    icon: "ElIcon",
  },
  runtimeConfig: {
    public: {
      serverIp: process.env.NUXT_PUBLIC_SERVER_IP,
    },
  },
});
