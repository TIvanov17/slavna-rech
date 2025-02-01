import { ConnectionType } from '../enums/connection-type.enum';
import { MemberResponse } from './member.models';

export type FriendConnectionRequest = {
  senderId: number;
  receiverId: number;
};

export type ChannelConnectionRequest = {
  userId: number;
  name: string;
  description: string;
};

export type UpdateChannelRequest = {
  connectionId: number;
  name: string;
  description: string;
};

export interface ConnectionResponse {
  id: number;
  name: string;
  description: string;
  createdOn: string;
  connectionType: ConnectionType;
  isActive: boolean;
  members: MemberResponse[];
}
