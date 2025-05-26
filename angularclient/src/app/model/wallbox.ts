import { WallboxOwner } from "./owner";

export interface Wallbox {
    id: number;
    physicalId: number;
    name: string;
    owner: WallboxOwner
}

export interface RentedWallbox {
    id: number;
    wallbox: Wallbox;
    startTime: Date;
    endTime: Date;
}
