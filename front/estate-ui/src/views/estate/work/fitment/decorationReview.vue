<template>
  <div class="app-container">
    <WorkOrderAudit :orderType="orderType"
                    :adoptPermission="adoptPermission"
                    :rejectPermission="rejectPermission"
    ></WorkOrderAudit>
  </div>
</template>

<script>
import WorkOrderAudit from "@/components/work/workOrderAudit";

export default {
  components: {
    WorkOrderAudit
  },
  data() {
    return {
      // 工单类型
      orderType: "4",
      adoptPermission: "decorate:audit:adopt",
      rejectPermission: "decorate:audit:reject"
    };
  }
};
</script>
<style scoped>
</style>
