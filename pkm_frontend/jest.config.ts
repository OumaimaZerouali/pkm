module.exports = {
  preset: 'jest-preset-angular',
  setupFilesAfterEnv: ['<rootDir>/src/setup-jest.ts'],
  transform: {
    '^.+\\.(ts|html)$': 'ts-jest',
  },
  moduleFileExtensions: ['ts', 'html', 'js'],
  testMatch: ['**/+(*.)+(spec).+(ts)'],
  transformIgnorePatterns: [
    'node_modules/(?!@angular|@ngrx|ngx-spring-boot|@testing-library/angular)',
  ],
};

