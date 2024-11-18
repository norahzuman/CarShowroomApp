export class CarModel {
    public id?: number;
    public vin: string;
    public maker: string;
    public model: string;
    public modelYear: number;
    public price: number;
    public showroomId?: number;

    constructor() {
        this.vin = '';
        this.maker = '';
        this.model = '';
        this.modelYear = new Date().getFullYear();
        this.price = 0;
        this.showroomId = undefined;
    }
}
