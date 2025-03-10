export type UserRequest = {
  username: string;
  email: string;
};

export type UserResponse = {
  id: number;
  username: string;
  email: string;
  createdOn: Date;
  isActive: boolean;
};

export type UserConnectionResponse = {
  connectionId: number;
  connectionCreatedOn: Date;
} & UserResponse;
