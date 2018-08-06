import { NewModuleModule } from './new-module.module';

describe('NewModuleModule', () => {
  let newModuleModule: NewModuleModule;

  beforeEach(() => {
    newModuleModule = new NewModuleModule();
  });

  it('should create an instance', () => {
    expect(newModuleModule).toBeTruthy();
  });
});
