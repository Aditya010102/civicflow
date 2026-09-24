import { TestBed } from '@angular/core/testing';

class AuthState {
  constructor() { }
}

describe('AuthState', () => {
  let service: AuthState;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AuthState],
    });
    service = TestBed.inject(AuthState);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
