export interface Metadata {
  name: string;
  generateName?: string;
  labels?: {
    [key: string]: string;
  } | null;
  annotations?: {
    [key: string]: string;
  } | null;
  version?: number | null;
  creationTimestamp?: string | null;
  deletionTimestamp?: string | null;
}

export interface LinkGroupSpec {
  displayName: string;
  priority?: number;
  // @deprecated
  links: string[];
}

export interface LinkSpec {
  url: string;
  displayName: string;
  logo?: string;
  description?: string;
  priority?: number;
  groupName?: string;
  featureList: FeatureDescDTO[]
  featureTitle: string
  footFeature: FootFeatureDTO
  name: string
  techList: TechParaDTO[]
  topImage: string
  topTitle: string
  videoSrc: string
  videoSubTitle: string
  videoTitle: string
  qrcode: string
  extraInfo:ExtraInfoDTO
}
export interface ExtraInfoDTO {
  extraMainTitle:string
  extraMainSubTitle:string
  firstImage:string
  firstDesc:string
  secondImage:string
  secondDesc:string
}

export interface FeatureDescDTO {
  image: string
  desc: string
  subDesc: string
}

export interface TechParaDTO {
  image: string
  name: string
  value: string
}

export interface FootFeatureDTO {
  image: string
  price: string
  featureParamList: FootTxtDesc[]
}

export interface FootTxtDesc {
  desc:string
}

export interface Link {
  spec: LinkSpec;
  apiVersion: string;
  kind: string;
  metadata: Metadata;
}

export interface LinkGroup {
  spec: LinkGroupSpec;
  apiVersion: string;
  kind: string;
  metadata: Metadata;
}

export interface LinkList {
  page: number;
  size: number;
  total: number;
  items: Array<Link>;
  first: boolean;
  last: boolean;
  hasNext: boolean;
  hasPrevious: boolean;
  totalPages: number;
}

export interface LinkGroupList {
  page: number;
  size: number;
  total: number;
  items: Array<LinkGroup>;
  first: boolean;
  last: boolean;
  hasNext: boolean;
  hasPrevious: boolean;
  totalPages: number;
}
