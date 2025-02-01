import { UserResponse } from './user.models';

export type MessageRequest = {
  senderId: number;
  connectionId: number;
  content: string;
};

export type MessageDTO = {
  sender: UserResponse;
  connectionId: number;
  content: string;
  createdOn: Date;
};
