import { MemberStatus } from '../enums/member-status.enum';
import { RoleName } from '../enums/role-name.enums';

export interface MemberResponse {
  connectionId: number;
  userId: number;
  username: string;
  email: string;
  joinDate: string;
  role: RoleName;
  isActive: boolean;
  status: MemberStatus;
}

export interface MemberRequest {
  connectionId: number;
  userId: number;
  roleName: RoleName;
}
