// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.

export const environment = {
  production: false,
  firebase: {
    apiKey: "AIzaSyCAtDOGWoNUZ_pLPE9Sy_WbGW0g5JYzte0",
    authDomain: "basic-security.firebaseapp.com",
    databaseURL: "https://basic-security.firebaseio.com",
    projectId: "basic-security",
    storageBucket: "basic-security.appspot.com",
    messagingSenderId: "403057872782"
  }
};
