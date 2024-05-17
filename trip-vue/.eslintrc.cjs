/* eslint-env node */
module.exports = {
  root: true,
  extends: ["plugin:vue/vue3-essential", "eslint:recommended"],
  parserOptions: {
    ecmaVersion: "latest",
  },
  globals: {
    daum: "readonly", // to solve daum no-undef error
    kakao: "readonly", // to solve kakao no-undef error
  },
};
