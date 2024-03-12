<script lang="ts" setup>
import { Toast, VButton, VModal, VSpace } from "@halo-dev/components";
import { inject, ref, computed, nextTick, watch, type Ref } from "vue";
import type { Link } from "@/types";
import apiClient from "@/utils/api-client";
import cloneDeep from "lodash.clonedeep";

const props = withDefaults(
  defineProps<{
    visible: boolean;
    link?: Link;
  }>(),
  {
    visible: false,
    link: undefined,
  }
);

const emit = defineEmits<{
  (event: "update:visible", value: boolean): void;
  (event: "close"): void;
}>();

const initialFormState: Link = {
  metadata: {
    name: "",
    generateName: "link-",
  },
  spec: {
    displayName: "",
    url: "",
    logo: "",
    groupName: "",
    featureList: [],
    featureTitle: "",
    footFeature: { image: "", price:"",featureParamList: [] },
    name: "",
    techList: [],
    topImage: "",
    topTitle: "",
    videoSrc: "",
    videoSubTitle: "",
    videoTitle: "",
    qrcode: "",
    extraInfo:{
      extraMainTitle:"",
      extraMainSubTitle:"",
      firstImage:"",
      firstDesc:"",
      secondImage:"",
      secondDesc:"" 
    },
  },
  kind: "Product",
  apiVersion: "cn.cszcyl.products/v1alpha1",
};

const formState = ref<Link>(cloneDeep(initialFormState));
const saving = ref<boolean>(false);
const formVisible = ref(false);

const groupQuery = inject<Ref<string>>("groupQuery", ref(""));

const isUpdateMode = computed(() => {
  return !!formState.value.metadata.creationTimestamp;
});

const modalTitle = computed(() => {
  return isUpdateMode.value ? "编辑产品" : "新建产品";
});

const onVisibleChange = (visible: boolean) => {
  emit("update:visible", visible);
  if (!visible) {
    emit("close");
  }
};

const handleResetForm = () => {
  formState.value = cloneDeep(initialFormState);
};

watch(
  () => props.visible,
  (visible) => {
    if (visible) {
      formVisible.value = true;
    } else {
      setTimeout(() => {
        formVisible.value = false;
        handleResetForm();
      }, 200);
    }
  }
);

watch(
  () => props.link,
  (link) => {
    if (link) {
      formState.value = cloneDeep(link);
    }
  }
);

const annotationsFormRef = ref();

const handleSaveLink = async () => {
  annotationsFormRef.value?.handleSubmit();
  await nextTick();

  const { customAnnotations, annotations, customFormInvalid, specFormInvalid } =
    annotationsFormRef.value || {};
  if (customFormInvalid || specFormInvalid) {
    return;
  }

  formState.value.metadata.annotations = {
    ...annotations,
    ...customAnnotations,
  };

  try {
    saving.value = true;
    if (isUpdateMode.value) {
      await apiClient.put<Link>(
        `/apis/cn.cszcyl.products/v1alpha1/products/${formState.value.metadata.name}`,
        formState.value
      );
    } else {
      formState.value.spec.groupName = groupQuery.value;
      await apiClient.post<Link>(
        `/apis/cn.cszcyl.products/v1alpha1/products`,
        formState.value
      );
    }

    Toast.success("保存成功");

    onVisibleChange(false);
  } catch (e) {
    console.error(e);
  } finally {
    saving.value = false;
  }
};
</script>
<template>
  <VModal
    :title="modalTitle"
    :visible="visible"
    :width="650"
    @update:visible="onVisibleChange"
  >
    <template #actions>
      <slot name="append-actions" />
    </template>

    <FormKit
      v-if="formVisible"
      id="link-form"
      v-model="formState.spec"
      name="link-form"
      type="form"
      :config="{ validationVisibility: 'submit' }"
      @submit="handleSaveLink"
    >
      <div class="md:grid md:grid-cols-4 md:gap-6">
        <div class="md:col-span-1">
          <div class="sticky top-0">
            <span class="text-base font-medium text-gray-900"> 常规 </span>
          </div>
        </div>
        <div class="mt-5 divide-y divide-gray-100 md:col-span-3 md:mt-0">
          <!-- <FormKit
            type="text"
            name="displayName"
            validation="required"
            label="网站名称"
          ></FormKit>
          <FormKit
            type="url"
            name="url"
            validation="required"
            label="网站地址"
          ></FormKit>
          <FormKit type="attachment" name="logo" label="Logo"></FormKit>
          <FormKit type="textarea" name="description" label="描述"></FormKit> -->

          <FormKit type="attachment" name="qrcode" label="产品二维码"></FormKit>

          <FormKit type="attachment" name="topImage" label="顶部图片"></FormKit>

          <FormKit
            type="textarea"
            name="topTitle"
            label="顶部产品文字描述"
          ></FormKit>

          <FormKit type="text" name="videoTitle" label="视频标题"> </FormKit>

          <FormKit
            type="text"
            name="videoSubTitle"
            label="视频子标题"
          ></FormKit>

          <FormKit type="attachment" name="videoSrc" label="视频地址"></FormKit>

          <FormKit
            type="text"
            name="featureTitle"
            label="特色说明标题"
          ></FormKit>

          <FormKit type="repeater" name="featureList" label="特色说明">
            <FormKit type="attachment" name="image" label="特色图片"> </FormKit>

            <FormKit type="text" name="desc" label="概要说明"> </FormKit>

            <FormKit type="text" name="subDesc" label="详细说明"> </FormKit>
          </FormKit>

          <FormKit type="repeater" name="techList" label="技术参数">
            <FormKit type="attachment" name="image" label="参数图片"> </FormKit>

            <FormKit type="text" name="name" label="参数名字"> </FormKit>

            <FormKit type="text" name="value" label="参数值"> </FormKit>
          </FormKit>

          <FormKit type="text" name="name" label="产品名称"></FormKit>

          <FormKit type="group" name="footFeature" label="底部说明">
            <FormKit type="attachment" name="image" label="说明图片"> </FormKit>
            <FormKit type="text" name="price" label="产品价格"> </FormKit>

            <FormKit
              type="repeater"
              name="featureParamList"
              label="产品特色(添加多个)"
            >
              <FormKit type="text" name="desc" label="特色名称"> </FormKit>
            </FormKit>
          </FormKit>

          <FormKit type="group" name="extraInfo" label="双栏图片及说明">
            <FormKit type="text" name="extraMainTitle" label="双栏主标题"></FormKit>
            <FormKit type="text" name="extraMainSubTitle" label="双栏子标题"></FormKit>
            <FormKit type="attachment" name="firstImage" label="第一张图片"> </FormKit>
            <FormKit type="text" name="firstDesc" label="第一张图片说明"></FormKit>
            <FormKit type="attachment" name="secondImage" label="第二张图片"> </FormKit>
            <FormKit type="text" name="secondDesc" label="第二张图片说明"></FormKit>

            <FormKit
              type="repeater"
              name="featureParamList"
              label="产品特色(添加多个)"
            >
              <FormKit type="text" name="desc" label="特色名称"> </FormKit>
            </FormKit>
          </FormKit>
        </div>
      </div>
    </FormKit>

    <div class="py-5">
      <div class="border-t border-gray-200"></div>
    </div>

    <div class="md:grid md:grid-cols-4 md:gap-6">
      <div class="md:col-span-1">
        <div class="sticky top-0">
          <span class="text-base font-medium text-gray-900"> 元数据 </span>
        </div>
      </div>
      <div class="mt-5 divide-y divide-gray-100 md:col-span-3 md:mt-0">
        <AnnotationsForm
          v-if="visible"
          :key="formState.metadata.name"
          ref="annotationsFormRef"
          :value="formState.metadata.annotations"
          kind="Product"
          group="cn.cszcyl.products"
        />
      </div>
    </div>

    <template #footer>
      <VSpace>
        <VButton
          :loading="saving"
          type="secondary"
          @click="$formkit.submit('link-form')"
        >
          提交
        </VButton>
        <VButton @click="onVisibleChange(false)">取消</VButton>
      </VSpace>
    </template>
  </VModal>
</template>
