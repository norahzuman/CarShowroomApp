export class ShowroomModel {
  public id: number;
  public name: string;
  public commercialRegistrationNumber: number;
  public contactNumber: number;
  public managerName: string;
  public address: string;

  constructor() {
    this.id = 0;
    this.name = '';
    this.commercialRegistrationNumber = 0;
    this.contactNumber = 0;
    this.managerName = '';
    this.address = '';
  }
}
