/* eslint-env node */
require("@rushstack/eslint-patch/modern-module-resolution");

module.exports = {
  root: true,
  extends: ["plugin:vue/vue3-essential", "eslint:recommended", "@vue/eslint-config-typescript"],
  parserOptions: {
    ecmaVersion: "latest",
  },
  globals: {
    daum: "readonly", // to solve daum no-undef error
    kakao: "readonly", // to solve kakao no-undef error
    Kakao: "readonly", // to solve kakao no-undef error
  },
};
