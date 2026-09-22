import { UserRole } from './user-role';

export interface UserResponse {
    id: number;
    email: string;
    role: UserRole;
    enabled: boolean;
    createdAt: string;
    updatedAt: string;
}