import "./styles/tailwind.css";
import "./styles/index.css";
import { definePlugin } from "@halo-dev/console-shared";
import LinkList from "@/views/LinkList.vue";
import { markRaw } from "vue";
import TablerBrandProducthunt from '~icons/tabler/brand-producthunt'

export default definePlugin({
  components: {},
  routes: [
    {
      parentName: "Root",
      route: {
        path: "/links",
        name: "Links",
        component: LinkList,
        meta: {
          menu: {
            name: "产品",
            group: "content",
            icon: markRaw(TablerBrandProducthunt),
          },
        },
      },
    },
  ],
});
